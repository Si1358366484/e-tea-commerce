import { createRouter, createWebHistory } from 'vue-router'

import layoutVue from '@/views/layout.vue'
import LoginVue from '@/views/login.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'

const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: layoutVue, children: [
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/resetpassword', component: UserResetPasswordVue }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

export default router