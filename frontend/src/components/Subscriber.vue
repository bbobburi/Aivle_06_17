<template>
    <div>
        <String
            label="SubscriberId"
            v-model="value.subscriberId"
            :editMode="editMode"
        />
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="SubscriptionType"
            v-model="value.subscriptionType"
            :editMode="editMode"
        />
        <Date
            label="StartedAt"
            v-model="value.startedAt"
            :editMode="editMode"
        />
        <Date
            label="ExpiredAt"
            v-model="value.expiredAt"
            :editMode="editMode"
        />
        <String
            label="Password"
            v-model="value.password"
            :editMode="editMode"
        />
        <ViewHistory
            offline
            label="viewHistory"
            v-model="value.viewHistory"
            :editMode="editMode"
            @change="change"
        />
        <membershipType
            offline
            label="MembershipType"
            v-model="value.membershipType"
            :editMode="editMode"
            @change="change"
        />
        <subscriptionStatus
            offline
            label="SubscriptionStatus"
            v-model="value.subscriptionStatus"
            :editMode="editMode"
            @change="change"
        />
        <Email
            offline
            label="Email"
            v-model="value.email"
            :editMode="editMode"
            @change="change"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Subscriber',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "subscribers",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
