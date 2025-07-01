<template>
  <v-container fluid>
    <v-row>
      <v-card
          v-for="(card, index) in cards"
          :key="index"
          class="mx-auto"
          style="height:300px; width:300px; margin-bottom:20px;"
          outlined
      >
        <v-list-item>
          <v-icon style="margin-top: 30%; margin-left: 37.5%;" color="primary" size="64">mdi-apps</v-icon>
        </v-list-item>
        <v-card-actions>
          <v-btn
              class="mx-auto"
              outlined
              rounded
              :to="card.link"
              style="font-weight:500; font-size:20px; border:solid 2px; max-width:250px; overflow:hidden; margin-top: 10%;"
          >
              {{ card.text }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Index',
  data: () => ({
    cards: [
      {
          text: "작가",
          link: "/authors",
      },
      {
          text: "작가",
          link: "/authors",
      },
      {
          text: "전자책",
          link: "/eBooks",
      },
      {
          text: "작가 이력 조회",
          link: "/lookupAuthors",
      },
      {
          text: "전자책 조회",
          link: "/lookupEbooks",
      },
      {
          text: "플랫폼",
          link: "/eBookPlatforms",
      },
      {
          text: "구독자",
          link: "/subscribers",
      },
      {
          text: "본인 정보 조회",
          link: "/lookUpMyInfos",
      },
      {
          text: "전자책",
          link: "/eBooks",
      },
      {
          text: "포인트",
          link: "/userPoints",
      },
      {
          text: "구독자 정보 조회",
          link: "/lookUpSubscribers",
      },
    ],
  }),
};
</script>
