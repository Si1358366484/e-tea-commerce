<script setup>
import avatar from '@/assets/cy.jpg'
import {userInfoService} from '@/api/user.js'
import { useTokenStore } from '@/stores/token.js'
import useUserInfoStore from '@/stores/userInfo.js'

const userInfoStore = useUserInfoStore()
const tokenStore = useTokenStore()
//调用函数获取用户信息
const getUserInfo = async () => {
    let result = await userInfoService()
    //数据存储到pinia
    userInfoStore.setInfo(result.data)
}
//getUserInfo()
// 点击下拉菜单的回调
import {useRouter} from 'vue-router'
const router = useRouter()
import { ElMessage, ElMessageBox } from 'element-plus'
const handleCommand = (command) => {
    if (command === 'logout') {
        //退出登录
        ElMessageBox.confirm(
    '你确定要推出吗',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => {
        //退出登录
        //1.清除token
        tokenStore.removeToken()
        userInfoStore.removeInfo()
        //2.跳转登录
        router.push('/login')
      ElMessage({
        type: 'success',
        message: '退出登录成功',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '用户取消了退出登录',
      })
    })
    }else{
        //路由
        router.push('/user/' + command)
    }
}
</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
                router>
                <el-menu-item index="/tea">
                    <el-icon>
                        <CoffeeCup />
                    </el-icon>
                    <span>茶叶信息管理</span>
                </el-menu-item>
                <el-menu-item index="/order">
                    <el-icon>
                        <ShoppingCart />
                    </el-icon>
                    <span>订单管理</span>
                </el-menu-item>
                <el-menu-item index="/comment">
                    <el-icon>
                        <Comment />
                    </el-icon>
                    <span>评论管理</span>
                </el-menu-item>
                <el-sub-menu >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetpassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>这里是茶叶电商系统后台：<strong>Administrators</strong></div>
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <!-- <el-avatar :src="userInfoStore.info.userPic?userInfoStore.info.userPic:avatar" /> -->
                        <el-avatar :src="avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <!-- command 条目被点击后触发，名字应该与路由表一致 -->
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetpassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>茶叶电商后台管理系统 ©2024 All Rights Reserved</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>