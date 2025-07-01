import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/authors',
      component: () => import('../components/ui/AuthorGrid.vue'),
    },
    {
      path: '/authors',
      component: () => import('../components/ui/AuthorGrid.vue'),
    },
    {
      path: '/eBooks',
      component: () => import('../components/ui/EBookGrid.vue'),
    },
    {
      path: '/lookupAuthors',
      component: () => import('../components/LookupAuthorView.vue'),
    },
    {
      path: '/lookupEbooks',
      component: () => import('../components/LookupEbookView.vue'),
    },
    {
      path: '/eBookPlatforms',
      component: () => import('../components/ui/EBookPlatformGrid.vue'),
    },
    {
      path: '/subscribers',
      component: () => import('../components/ui/SubscriberGrid.vue'),
    },
    {
      path: '/lookUpMyInfos',
      component: () => import('../components/LookUpMyInfoView.vue'),
    },
    {
      path: '/eBooks',
      component: () => import('../components/ui/EBookGrid.vue'),
    },
    {
      path: '/userPoints',
      component: () => import('../components/ui/UserPointGrid.vue'),
    },
    {
      path: '/lookUpSubscribers',
      component: () => import('../components/LookUpSubscriberView.vue'),
    },
  ],
})

export default router;
