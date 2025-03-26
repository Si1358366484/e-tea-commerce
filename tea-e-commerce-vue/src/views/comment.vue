<script setup>
import { Check, Delete } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage } from "element-plus";
import { onMounted, onUnmounted, nextTick } from "vue";
import { getCommentListService } from "@/api/comment";
import { throttle } from "lodash-es";
const comments = ref([]); // 数据列表
const tableRef = ref();
const page = ref(1); // 当前页码
const pageSize = ref(10); // 每页数量
const loading = ref(false); // 加载状态
const hasMore = ref(true); // 是否无更多数据
let scrollWrapper = null;
const initialLoading = ref(true) // 新增初始加载状态


// 获取数据方法
const getCommentList = async () => {
  try {
    loading.value = true
    let params = {
      pageNum: page.value,
      pageSize: pageSize.value,
    }
    // 添加500ms延迟展示加载效果
    await new Promise(resolve => setTimeout(resolve, 1000))
    const result = await getCommentListService(params)

    comments.value = [...comments.value, ...result.data.comments]
    hasMore.value = result.data.hasMore

    if (comments.value.length === 0 && hasMore.value) {
      page.value++
      await getCommentList()
    }
  } finally {
    loading.value = false
    initialLoading.value = false // 关闭初始加载
  }
}
// 加载更多数据
const loadMore = async () => {
  if (!hasMore.value || loading.value) return
  page.value++
  await getCommentList()
  ElMessage.success("数据加载成功")
}

// 滚动处理函数
const handleScroll = throttle(() => {
  if (!scrollWrapper || !hasMore.value || loading.value) return;

  const { scrollTop, clientHeight, scrollHeight } = scrollWrapper;
  const threshold = 50;

  if (scrollTop + clientHeight >= scrollHeight - threshold) {
    loadMore();
  }
}, 500);
// 初始化滚动监听
onMounted(async () => {
  await getCommentList()
  
  // 确保表格渲染完成后再获取滚动容器
  await nextTick()
  scrollWrapper = tableRef.value?.$el?.querySelector('.el-table__body-wrapper')
  
  if (scrollWrapper) {
    scrollWrapper.addEventListener('scroll', handleScroll)
  }
})

// 清除监听器
onUnmounted(() => {
  if (scrollWrapper) {
    scrollWrapper.removeEventListener('scroll', handleScroll)
  }
})
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>评论管理</span>
      </div>
    </template>
    <el-table
      :data="comments"
      class="table"
      height="482"
      @scroll="handleScroll"
      ref="tableRef"
    >
      <!-- 添加 align="center" 让文字居中 -->
      <el-table-column label="序号" width="100" type="index" align="center">
      </el-table-column>
      <el-table-column
        label="评论用户"
        prop="customerName"
        align="center"
      ></el-table-column>
      <el-table-column
        label="评论商品"
        prop="teaName"
        align="center"
      ></el-table-column>
      <el-table-column
        label="评分"
        width="100"
        prop="grade"
        align="center"
      ></el-table-column>
      <el-table-column
        label="评论内容"
        width="450"
        prop="content"
        align="center"
      ></el-table-column>
      <el-table-column label="评论状态" prop="state" align="center">
        <template #default="{ row }">
          <!-- 修改绿色为更亮的颜色 -->
          <span v-if="row.state === '已通过'" style="color: #32CD32">已通过</span>
          <span v-else-if="row.state === '待审核'" style="color: #A9A9A9">待审核</span>
          <span v-else-if="row.state === '已驳回'" style="color: red">已驳回</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="{}">
          <el-button :icon="Check" circle type="success"></el-button>
          <el-button :icon="Delete" circle type="danger"></el-button>
        </template>
      </el-table-column>
      <template #append>
        <div v-if="loading" class="loading-animation">
          <!-- 添加 flex 布局样式 -->
          <div style="display: flex; justify-content: center; align-items: center;">
            <el-icon :size="20">
              <el-loading />
            </el-icon>
            <!-- 使用项目中已有的样式，文本居中 -->
            <span style="text-align: center; margin-left: 8px;">正在加载更多数据...</span>
          </div>
        </div>
        <div v-if="!hasMore" class="no-more-text">
          <!-- 使用项目中已有的 el-divider 样式 -->
          <el-divider content-position="center">已经到底啦~</el-divider>
        </div>
      </template>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;

    span {
      font-size: 20px;
      font-weight: bold;
      color: #333;
    }
  }

  .table {
    width: 100%;
  }
}
</style>