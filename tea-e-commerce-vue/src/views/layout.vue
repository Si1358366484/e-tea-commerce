<script setup>
import avatar from '@/assets/default.png'
const adminData = localStorage.getItem('adminToken')
const parsedData = JSON.parse(adminData); // 解析为对象

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
        localStorage.removeItem("adminToken");
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

// 添加日期和时间相关的函数
const getCurrentDate = () => {
    const date = new Date()
    return `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日`
}
const getCurrentTime = () => {
    const date = new Date()
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}:${String(date.getSeconds()).padStart(2, '0')}`
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
                    <el-menu-item index="/admin/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/resetpassword">
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
                        <el-avatar :src="parsedData.adminPic?parsedData.adminPic:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <!-- command 条目被点击后触发，名字应该与路由表一致 -->
                            <el-dropdown-item command="/avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="/resetpassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view v-slot="{ Component }">
                    <component :is="Component" v-if="$route.path !== '/'" />
                    <div v-else class="welcome-container">
                        <div class="welcome-header">
                            <h2>今天是 {{ getCurrentDate() }}</h2>
                        </div>
                        
                        <el-row :gutter="20" class="data-overview">
                            <el-col :span="12">
                                <el-card shadow="hover">
                                    <template #header>
                                        <div class="card-header">
                                            <div class="title">
                                                <el-icon><Goods /></el-icon>
                                                <span>商品总数</span>
                                            </div>
                                        </div>
                                    </template>
                                    <div class="card-number">358</div>
                                </el-card>
                            </el-col>
                            <el-col :span="12">
                                <el-card shadow="hover">
                                    <template #header>
                                        <div class="card-header">
                                            <div class="title">
                                                <el-icon><ShoppingCart /></el-icon>
                                                <span>订单总数</span>
                                            </div>
                                        </div>
                                    </template>
                                    <div class="card-number">1,234</div>
                                </el-card>
                            </el-col>
                        </el-row>

                        <el-row :gutter="20" class="data-overview" style="margin-top: 20px;">
                            <el-col :span="12">
                                <el-card shadow="hover">
                                    <template #header>
                                        <div class="card-header">
                                            <div class="title">
                                                <el-icon><ChatDotRound /></el-icon>
                                                <span>评论数量</span>
                                            </div>
                                        </div>
                                    </template>
                                    <div class="card-number">526</div>
                                </el-card>
                            </el-col>
                            <el-col :span="12">
                                <el-card shadow="hover">
                                    <template #header>
                                        <div class="card-header">
                                            <div class="title">
                                                <el-icon><User /></el-icon>
                                                <span>用户数量</span>
                                            </div>
                                        </div>
                                    </template>
                                    <div class="card-number">892</div>
                                </el-card>
                            </el-col>
                        </el-row>

                        <el-card class="system-info" style="margin-top: 20px;">
                            <template #header>
                                <div class="card-header">
                                    <span>系统信息</span>
                                </div>
                            </template>
                            <el-descriptions :column="2" :border="true">
                                <el-descriptions-item label="系统版本">v1.0.0</el-descriptions-item>
                                <el-descriptions-item label="Node版本">v16.0.0</el-descriptions-item>
                                <el-descriptions-item label="操作系统">Windows 11</el-descriptions-item>
                                <el-descriptions-item label="运行环境">Chrome 120</el-descriptions-item>
                                <el-descriptions-item label="当前用户">Administrators</el-descriptions-item>
                                <el-descriptions-item label="登录时间">{{ getCurrentTime() }}</el-descriptions-item>
                            </el-descriptions>
                        </el-card>
                    </div>
                </router-view>
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

.data-overview {
    .el-card {
        .card-header {
            .title {
                display: flex;
                align-items: center;
                gap: 8px;  // 设置图标和文字之间的间距
                
                .el-icon {
                    font-size: 16px;
                }
            }
        }

        .card-number {
            font-size: 28px;
            font-weight: bold;
            color: #409EFF;
            text-align: center;
            padding: 10px 0;
        }
    }
}
</style>