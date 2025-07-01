# Author Service (작가관리 시스템)

작가 등록부터 콘텐츠 작성, 전자책 출간 요청, 비공개 요청까지 담당하는 마이크로서비스입니다.  
Kafka 기반 이벤트 발행 및 수신 구조를 따르며, 관리자 시스템과 비동기적으로 연결됩니다.

---

## 📦 Bounded Context

**AuthorBoundedContext**
- 작가 등록
- 콘텐츠 작성
- 전자책 출간 요청 및 취소
- 전자책 비공개 요청

---

## 🧩 책임 범위

이 시스템은 작가 활동에 대한 상태를 관리하고,  
그 결과를 이벤트로 발행하여 **관리자 시스템** 및 **서재 플랫폼**에서 심사 및 최종 처리를 할 수 있도록 합니다.

---

## 🔀 이벤트 흐름

| 시나리오 | 발행 이벤트 | 설명 |
|----------|--------------|------|
| 작가 등록 | `RegisteredAuthor` | 관리자의 작가 승인 필요 |
| 콘텐츠 작성 | `WrittenContent` | 관리자의 콘텐츠 등록 심사 |
| 출간 요청 | `RequestPublish` | 전자책 출간 심사 |
| 출간 요청 취소 | `RequestPublishCanceled` | 출간 심사 철회 |
| 전자책 비공개 | `ListOutEbookRequested` | ebook 상태 변경 요청 |

---

## 📡 Kafka 연동

- **Output 토픽**: `event-out` (이벤트 발행)
- **Input 토픽**: `event-in` (다른 시스템 이벤트 수신)

```java
public interface KafkaProcessor {
    String INPUT = "event-in";
    String OUTPUT = "event-out";
}

