//导入request.js工具
import request from '@/utils/request.js'
export const getCommentListService = (params)=>{
    return request.get('/admin/reviewList',{
        params
    })
}