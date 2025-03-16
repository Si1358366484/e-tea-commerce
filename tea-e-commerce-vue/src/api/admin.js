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