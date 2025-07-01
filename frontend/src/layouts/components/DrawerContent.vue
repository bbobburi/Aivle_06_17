<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '작가 관리' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/authors',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '관리자 시스템' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/authors',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '전자책',
                to: '/eBooks',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '작가 이력 조회',
                to: '/lookupAuthors',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '전자책 조회',
                to: '/lookupEbooks',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '서재 플랫폼' }" />
        <VerticalNavLink
            :item="{
                title: '플랫폼',
                to: '/eBookPlatforms',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '구독자' }" />
        <VerticalNavLink
            :item="{
                title: '구독자',
                to: '/subscribers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '본인 정보 조회',
                to: '/lookUpMyInfos',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'AI 시스템' }" />
        <VerticalNavLink
            :item="{
                title: '전자책',
                to: '/eBooks',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '포인트 시스템' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/userPoints',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독자 정보 조회',
                to: '/lookUpSubscribers',
            }"
        />
    </ul>
</template>
