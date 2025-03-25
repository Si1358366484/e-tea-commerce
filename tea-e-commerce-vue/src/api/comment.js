//导入request.js工具
import request from '@/utils/request.js'
export const getCommentListService = ()=>{
    return request.get('/admin/reviewList')
}