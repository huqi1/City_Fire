<template>
  <div class="mod-home">
    <h3>城市消防监控后台管理系统简介</h3>
    <p>
      &#8195;&#8195; 随着城市化进程的加快，大型建筑、公共建筑的逐步增多，对大型化建筑消防设施的管理，确保大型建筑消防设施的正常运行至关重要，而其中消防设施的管理又是重中之重。消防设施主要分为灭火类（灭火器、消火栓等）、报警类（火灾探测器、报警器等），往往一个楼层存在若多的消防设备，对消防设备有效的规范管理迫在眉睫。
    </p>
    <p>
      &#8195;&#8195; 本系统利用了SpringBoot和Vue实现了对城市消防监控设备的在线统计管理，系统具备区域管理、设备分类管理、设备管理、预警管理和报表统计五大功能，通过设备分类，将所有消防设备进行唯一编码，有效的满足了消防设备的在线统计查看，设备异常后报警更换，及对设备维修更换率生成报表在线查看。
    </p>
    <h3>主要技术</h3>
    <ul>前端
      <li>技术栈：Vue2.0 、element-ui、Bootstrap、Jquary</li>
      <li>网络请求：axios</li>
      <li>Router：动态添加</li>
      <li>报表统计：echarts</li>
      <li>地图展示：高德地图(Vue-aMap)，支持定位、搜索、展示搜索文本卡窗</li>
      <li>其他：本地测试通过mock配置对接口数据／mock模拟数据进行切换</li>
    </ul>
    <ul>后台
      <li>技术栈：  SpringBoot、MyBatisPlus</li>
      <li>权限控制： Shiro</li>
      <li>跨域： 基于Token验证和Cors配置</li>
    </ul>
    <ul>部署
      <li>Nginx：      待定</li>
      <li>Redis：     待定</li>
    </ul>
  </div>
</template>

<script>
  export default {
  }
</script>

<style>
  .mod-home {
    line-height: 1.5;
  }
</style>

