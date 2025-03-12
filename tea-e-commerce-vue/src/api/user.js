//导入request.js工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData)=>{
    //借助于UrlSearchParam完成传递
    const params = new URLSearchParams()
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/user/register',params)
}

export const userLoginService = (loginData)=>{
     //借助于UrlSearchParam完成传递
    const params = new URLSearchParams()
    for(let key in loginData){
        params.append(key,loginData[key]);
    }
    return request.post('/user/login',params)
}

//获取用户详细信息
export const userInfoService = ()=>{
    return request.get('/user/userInfo')
}
//修改个人信息
export const userInfoUpdateService = (userInfoData)=>{
    return request.put('/user/update',userInfoData)
}
export const userUpdateAvatarService = (avatarUrl)=>{
    const params = new URLSearchParams()
    params.append('avatarUrl',avatarUrl)
    return request.patch('/user/updateAvatar',params)
}
export const userUpdatePasswordService = (params)=>{
    return request.patch('/user/updatePwd',params)
}