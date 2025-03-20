import { createRouter, createWebHistory } from 'vue-router'

import layoutVue from '@/views/layout.vue'
import LoginVue from '@/views/Login.vue'
import TeaVue from '@/views/tea.vue'
import OrderVue from '@/views/order.vue'
import CommentVue from '@/views/comment.vue'
import AdminAvatar from '@/views/admin/AdminAvatar.vue'
import AdminResetPassword from '@/views/admin/AdminResetPassword.vue'

const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: layoutVue, children: [
            { path: '/tea', component: TeaVue },
            { path: '/order', component: OrderVue },
            { path: '/comment', component: CommentVue },
            { path: '/admin/avatar', component: AdminAvatar },
            { path: '/admin/resetpassword', component: AdminResetPassword }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

export default router