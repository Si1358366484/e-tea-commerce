//导入request.js工具
import request from '@/utils/request.js'
export const getOrderListService = ()=>{
    return request.get('/admin/orderList')
}
export const updateStateService = (state,orderReference)=>{
    console.log(state,orderReference);
    
    return request.put('/admin/orderUpdate', { 
        state: state, 
        orderReference:orderReference
    });
}
export const searchOrderService = (orderReference)=>{
    return request.get(`/admin/orderSearch/${orderReference}`);
}