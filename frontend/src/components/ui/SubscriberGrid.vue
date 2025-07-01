<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
            </div>
            <LookUpMyInfo @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></LookUpMyInfo>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>UserId</th>
                        <th>SubscriptionType</th>
                        <th>StartedAt</th>
                        <th>ExpiredAt</th>
                        <th>viewHistory</th>
                        <th>MembershipType</th>
                        <th>SubscriptionStatus</th>
                        <th>Password</th>
                        <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="UserId">{{ val.userId }}</td>
                            <td class="whitespace-nowrap" label="SubscriptionType">{{ val.subscriptionType }}</td>
                            <td class="whitespace-nowrap" label="StartedAt">{{ val.startedAt }}</td>
                            <td class="whitespace-nowrap" label="ExpiredAt">{{ val.expiredAt }}</td>
                            <td class="whitespace-nowrap" label="viewHistory">{{ val.viewHistory }}</td>
                            <td class="whitespace-nowrap" label="MembershipType">{{ val.membershipType }}</td>
                            <td class="whitespace-nowrap" label="SubscriptionStatus">{{ val.subscriptionStatus }}</td>
                            <td class="whitespace-nowrap" label="Password">{{ val.password }}</td>
                            <td class="whitespace-nowrap" label="Email">
                                <Email :editMode="false" :inList="true" v-model="val.email"></Email>
                            </td>
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Subscriber 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Subscriber :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Subscriber 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="SubscriberId" v-model="selectedRow.subscriberId" :editMode="true"/>
                            <String label="UserId" v-model="selectedRow.userId" :editMode="true"/>
                            <String label="SubscriptionType" v-model="selectedRow.subscriptionType" :editMode="true"/>
                            <Date label="StartedAt" v-model="selectedRow.startedAt" :editMode="true"/>
                            <Date label="ExpiredAt" v-model="selectedRow.expiredAt" :editMode="true"/>
                            <String label="Password" v-model="selectedRow.password" :editMode="true"/>
                            <ViewHistory offline label="viewHistory" v-model="selectedRow.viewHistory" :editMode="true"/>
                            <membershipType offline label="MembershipType" v-model="selectedRow.membershipType" :editMode="true"/>
                            <subscriptionStatus offline label="SubscriptionStatus" v-model="selectedRow.subscriptionStatus" :editMode="true"/>
                            <Email offline label="Email" v-model="selectedRow.email" :editMode="true"/>
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue'


export default {
    name: 'subscriberGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'subscribers',
    }),
    watch: {
    },
    methods:{
    }
}

</script>