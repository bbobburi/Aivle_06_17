# Point System (포인트 시스템)

## 📋 개요

Point System은 사용자의 포인트를 관리하는 마이크로서비스입니다. 사용자 가입 시 멤버십 타입에 따른 포인트 지급과 E-Book 구매 시 구독 상태에 따른 포인트 차감 기능을 제공합니다.

---

## ⚙️ 전체 동작 로직 요약

### 1. 포인트 지급 로직
- 사용자가 가입하면 `SignedUp` 이벤트가 발생합니다.
- 이 이벤트는 Kafka를 통해 오거나, 테스트 컨트롤러에서 직접 트리거될 수 있습니다.
- `PolicyHandler.wheneverSignedUp_CheckMembership(SignedUp signedUp)`가 호출됩니다.
- 이벤트의 `membershipType` 값이 `KT`이면 5,000포인트, 아니면 1,000포인트를 지급합니다.
- 지급할 포인트와 userId를 담은 `GivePointCommand` 객체를 생성하여 `UserPoint.givePoint(GivePointCommand)`를 호출합니다.
- 내부적으로 새로운 UserPoint 엔티티를 생성하고, userId와 point를 저장합니다.
- 저장 시 JPA의 `@PostPersist`에 의해 `GivenPoint` 이벤트가 발행됩니다.

### 2. 포인트 차감 로직
- 프론트엔드 등에서 `/userPoints/deduct`로 포인트 차감 요청이 들어옵니다.
- 요청에는 `userId`, `ebookId`, `price`가 포함됩니다.
- `UserPoint.deductPoint(PointDeductionRequest)`가 호출됩니다.
- 이 메서드는 차감 전, 해당 사용자의 구독 상태를 확인해야 합니다.
- `SubscriberService.getSubscriberStatus(String userId)`를 통해 Subscriber System의 `/subscribers/status?userId=...` 엔드포인트에 GET 요청을 보냅니다.
- Subscriber System은 내부적으로 자신의 DB(SubscriberRepository)에서 userId로 구독 상태를 조회해 응답합니다.
- 응답받은 구독 상태가 `SUBSCRIBED`라면 포인트를 차감하지 않고, 0포인트 차감 메시지를 반환합니다.
- 구독 상태가 `SUBSCRIBED`가 아니면, UserPoint 테이블에서 해당 사용자의 포인트를 조회합니다.
- 보유 포인트가 price보다 적으면 오류 메시지를 반환합니다.
- 충분하다면 price만큼 차감하고, 결과를 저장합니다.

### 3. 구독 상태 확인 로직 (Subscriber System)
- Subscriber System의 컨트롤러가 userId를 파라미터로 받아 SubscriberRepository에서 해당 사용자를 조회합니다.
- 사용자가 있으면 subscriptionStatus를 포함한 응답을 반환합니다.
- 없으면 실패 메시지를 반환합니다.

### 4. 데이터 구조
- **UserPoint**: userId(Primary Key), point(보유 포인트)
- **Subscriber**: subscriberId, userId, subscriptionType, membershipType, subscriptionStatus 등

### 5. 이벤트/명령 객체
- **SignedUp**: 가입 이벤트, userId, membershipType 등 포함
- **GivePointCommand**: 포인트 지급 명령, userId, point 포함
- **PointDeductionRequest/Response**: 포인트 차감 요청/응답 DTO

### 6. 전체 흐름 요약
- **가입 시**: membershipType에 따라 포인트 지급 (KT: 5000, NORMAL: 1000)
- **E-Book 구매 시**: 구독 상태가 SUBSCRIBED면 0포인트, 아니면 price만큼 차감
- **구독 상태 확인**: Point System이 Subscriber System의 REST API를 통해 실시간으로 확인
- **모든 데이터 저장/변경**: UserPoint 테이블에 반영

---

## 📁 주요 파일 및 클래스 역할

- **UserPoint.java**: 사용자별 포인트 관리, 지급/차감 메서드 포함, 지급 시 GivenPoint 이벤트 발행
- **PolicyHandler.java**: SignedUp 이벤트 수신 및 membershipType에 따라 포인트 지급 분기
- **SubscriberService.java**: Subscriber System의 구독 상태를 GET 방식으로 조회
- **UserPointController.java**: `/userPoints/deduct` 엔드포인트로 포인트 차감 처리
- **TestController.java**: `/test/signup-kt`, `/test/signup-normal` 엔드포인트로 직접 이벤트 트리거 가능 (Kafka 없이도 포인트 지급 로직 테스트 가능)
- **SubscriberController.java (Subscriber System)**: `/subscribers/status?userId=...`로 구독 상태 반환

---

## 🗄️ 데이터베이스 구조 예시

### UserPoint 테이블
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| userId | VARCHAR | 사용자 ID (Primary Key) |
| point | INTEGER | 보유 포인트 |

### Subscriber 테이블 (Subscriber System)
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| subscriberId | VARCHAR | 구독자 ID (Primary Key) |
| userId | VARCHAR | 사용자 ID |
| subscriptionType | VARCHAR | 구독 타입 |
| membershipType | VARCHAR | 멤버십 타입 (KT/NORMAL) |
| subscriptionStatus | VARCHAR | 구독 상태 (SUBSCRIBED 등) |

---

## 💡 핵심 비즈니스 로직 요약

- **가입 시**: membershipType이 KT면 5000, 아니면 1000포인트 지급
- **E-Book 구매 시**: 구독 상태가 SUBSCRIBED면 0포인트, 아니면 price만큼 차감
- **구독 상태 확인**: Point System이 Subscriber System의 REST API를 통해 실시간으로 확인
- **포인트 부족 시**: 차감 거부 및 오류 메시지 반환
- **모든 데이터 저장/변경**: UserPoint 테이블에 반영

---

## ⚠️ 주의사항

- 포인트 부족 시 차감 거부 및 오류 메시지 반환
- 구독 상태 확인 실패 시 오류 메시지 반환
- 모든 데이터 변경은 트랜잭션으로 처리되어 일관성 보장

---

이 README는 실제 코드의 동작 흐름과 구조를 중심으로 작성되었습니다. 각 단계별 상세 구현은 소스코드를 참고하세요.
