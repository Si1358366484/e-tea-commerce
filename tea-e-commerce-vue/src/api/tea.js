//导入request.js工具
import request from '@/utils/request.js'

//提供调用茶叶列表接口的函数
export const teaListService = (params)=>{
    return request.get('/admin/teaList',{params})
}

//提供调用删除茶叶接口的函数
export const teaDeleteServise = (id)=>{
    return request.delete(`/admin/tea/${id}`)
}

//提供调用添加茶叶接口的函数    
export const teaAddService = (teaData)=>{
    return request.post('/admin/tea',teaData)
}

//提供调用编辑茶叶接口的函数
export const teaEditService = (teaData)=>{
    return request.put(`/admin/tea/${teaData.id}`,teaData)
}
