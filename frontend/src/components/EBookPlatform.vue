<template>
    <div>
        <Number
            label="Pid"
            v-model="value.pid"
            :editMode="editMode"
        />
        <String
            label="Ebooks"
            v-model="value.ebooks"
            :editMode="editMode"
        />
        <Date
            label="RegisteredAt"
            v-model="value.registeredAt"
            :editMode="editMode"
        />
        <String
            label="CoverImage"
            v-model="value.coverImage"
            :editMode="editMode"
        />
        <String
            label="Summary"
            v-model="value.summary"
            :editMode="editMode"
        />
        <Number
            label="Prices"
            v-model="value.prices"
            :editMode="editMode"
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
    name: 'EBookPlatform',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "eBookPlatforms",
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
