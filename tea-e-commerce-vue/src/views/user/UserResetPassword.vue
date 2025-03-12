<script setup>
import { ref } from 'vue'
import {useRouter} from 'vue-router'
const router = useRouter()

//定义数据模型
const updateData = ref({
    oldPassword:'',
    newPassword:'',
    rePassword:''
})
const checkNewPassword = (rule,value,callback)=>{
    if(value == ''){
        callback(new Error('请输入密码'))
    }else if (value === updateData.value.oldPassword){
        callback(new Error('新密码不能与原密码相同'))
    }else if (value.length < 5 || value.length > 16){
        callback(new Error('长度为5-16位字符'))
    }else{
        callback()
    }
}
const checkRePassword = (rule,value,callback)=>{
    if(value == ''){
        callback(new Error('请再次确定密码'))
    }else if (value!== updateData.value.newPassword){
        callback(new Error('请确定两次输入密码一致'))
    }else{
        callback()
    }
}

const rules = {
    oldPassword:[
        {required:true,message:'请输入密码',trigger: 'blur'},
        {min:5,max:16,message:'长度为5-16位字符',trigger:'blur'},
    ],
    newPassword:[
        {required:true,validator: checkNewPassword,trigger: 'blur'},
    ],
    rePassword:[
        {required: true,validator: checkRePassword, trigger:'blur'}
    ]
}
import { userUpdatePasswordService } from '@/api/user.js'
import { ElMessage } from 'element-plus'

const updateUserPwd = async ()=>{
    let result = await userUpdatePasswordService(updateData.value)
    ElMessage.success(result.message?result.message : '修改密码成功,请重新登录')
    router.push('/login')
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="updateData" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="原密码" prop="oldPassword">
                        <el-input v-model="updateData.oldPassword" placeholder="请输入旧密码"></el-input>
                    </el-form-item>
                    <el-form-item label="修改新密码" prop="newPassword">
                        <el-input v-model="updateData.newPassword" placeholder="请输入新密码"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePassword">
                        <el-input v-model="updateData.rePassword" placeholder="请输入再次密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserPwd">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>