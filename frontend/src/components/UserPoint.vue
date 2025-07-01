<template>
    <div>
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <Number
            label="Point"
            v-model="value.point"
            :editMode="editMode"
        />
        <Number
            offline
            label="pointHistory"
            v-model="value.pointHistory"
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
    name: 'UserPoint',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "userPoints",
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
