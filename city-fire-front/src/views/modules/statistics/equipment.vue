<template>
  <div>
    <div class="chosedate">
      <span class="demonstration">选择统计时间:</span>
      <el-date-picker
        v-model="chosedate"
        type="datetimerange"
        :picker-options="pickerOptions2"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        align="right">
      </el-date-picker>
      <el-button type="primary" @click="getDataList()">搜索</el-button>
    </div>
  <div class="mod-demo-echarts">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartLineBox" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  </div>
</template>

<script>
  import echarts from 'echarts'
  export default {
    data () {
      return {
        chartLine: null,
        pickerOptions2: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        chosedate:[],
        date:[],
        community:[],
        pageData:{}


      }
    },
    mounted () {
      var end = new Date()
      var start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      var startdate = this.formatDate(start,"yyyy-MM-dd")
      var enddate =  this.formatDate(end,"yyyy-MM-dd")

      this.chosedate = [startdate, enddate]
      this.getDataList()

    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartLine) {
        this.chartLine.resize()
      }
    },
    methods: {
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/statistics/equipment'),
          method: 'get',
          params: this.$http.adornParams({
            'startDate': this.chosedate[0]+" 00:00:00",
            'endDate': this.chosedate[1]+" 23:59:59"
          })
        }).then(({data}) => {
          this.pageData =JSON.parse(JSON.stringify(data.page, null, 4));
          this.date = data.date
          this.community = data.community
          console.log("pageData = ", this.pageData)
          console.log("date = ", this.date)
          console.log("community = ", this.community)
          this.initChartLine()
        })
      },
      // 折线图
      initChartLine () {
        var option = {
          'title': {
            'text': '设备数量统计图'
          },
          'tooltip': {
            'trigger': 'axis'
          },
          'legend': {
            'data': this.community
          },
          'grid': {
            'left': '3%',
            'right': '4%',
            'bottom': '3%',
            'containLabel': true
          },
          'toolbox': {
            'feature': {
              'saveAsImage': { }
            }
          },
          'xAxis': {
            'type': 'category',
            'boundaryGap': false,
            'data':  this.date
          },
          'yAxis': {
            'type': 'value'
          },
          'series': []
        }
        var  communityLength = this.community.length
        for(var i =0;i<communityLength; i++){
          var  series = {
              'name': this.community[i],
              'type': 'line',
              'data': [ ]
            }
           var key = this.community[i]
          var communityData = JSON.parse(JSON.stringify(this.pageData[key], null, 4))
          console.log("communityData = ",communityData)
          var dateLength = this.date.length
            for (var j =0;j<dateLength;j++){
              var  key2 = this.date[j]
              series.data.push(parseInt(communityData[key2].sumEqunum))
            }
         option.series.push(series)
        }
        this.chartLine = echarts.init(document.getElementById('J_chartLineBox'))
        console.log("option = ", JSON.stringify(option,null,4))
        this.chartLine.setOption(option)
        window.addEventListener('resize', () => {
          this.chartLine.resize()
        })
      },
      formatDate(date,fmt){
        if(/(y+)/.test(fmt)){
          fmt = fmt.replace(RegExp.$1, (date.getFullYear()+'').substr(4-RegExp.$1.length));
        }
        let o = {
          'M+': date.getMonth()+1,
          'd+': date.getDate(),
          'h+': date.getHours(),
          'm+': date.getMinutes(),
          's+': date.getSeconds()
        }
        for(let k in o){
          let str = o[k]+'';
          if(new RegExp(`(${k})`).test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length===1)?str:this.padLeftZero(str));
          }
        }
        return fmt;
      },
    padLeftZero(str){
      return ('00'+str).substr(str.length);
    }
    }
  }
</script>

<style lang="scss">
  .mod-demo-echarts {
    > .el-alert {
      margin-bottom: 10px;
    }
    > .el-row {
      margin-top: -10px;
      margin-bottom: -10px;
      .el-col {
        padding-top: 10px;
        padding-bottom: 10px;
      }
    }
    .chart-box {
      min-height: 400px;
    }
  }
  .chosedate{
    margin-bottom: 10px;
    text-align: center;
  }
</style>

