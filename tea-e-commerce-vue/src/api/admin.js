//导入request.js工具
import request from '@/utils/request.js'
//管理员登录
export const adminLoginService = (loginData)=>{
  //借助于UrlSearchParam完成传递
 const params = new URLSearchParams()
 for(let key in loginData){
     params.append(key,loginData[key]);
 }
 return request.post('/admin/login',params)
}
//修改密码
export const adminUpdatePasswordService = (updateData)=>{
  return request.patch('/admin/updatePwd',updateData);
}
export const adminUpdateAvatarService = (avatarUrl)=>{
  const params = new URLSearchParams()
  params.append('avatarUrl',avatarUrl)
  return request.patch('/admin/updateAvatar',params)
}