<template>
  <div class="mod-demo-echarts">


    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div id="J_chartPieBox" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import echarts from 'echarts'
  export default {
    data () {
      return {
        chartPie: null,
        pageData:[{
          district:'',
          num:''
        }],
        nameFrom:[],
        numFrom:[],
        seriesData:[{
          value:'',
          name:''
        }]
      }
    },
    mounted () {
      this.getDataList()
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartPie) {
        this.chartPie.resize()
      }

    },
    methods: {
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/statistics/location'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.pageData =data.page;
          var length =  this.pageData.length
          for(var i = 0;i<length;i++){
            this.nameFrom.push(this.pageData[i].district)
            this.numFrom.push(this.pageData[i].num)
            var item = {
                value:this.pageData[i].num,
                name:this.pageData[i].district
            }
            this.seriesData.push(item)
          }
          this.initChartPie()
        })
      },
      // 饼状图
      initChartPie () {
        var option = {
          title: {
            text: '覆盖小区统计',
            subtext: '按成都市已覆盖区统计',
            left: 'center',
            top: 20
          },
          //提示主键框
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          toolbox: {
            feature: {
              'saveAsImage': { }
            }
          },
          /*visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
              colorLightness: [0, 1]
            }
          },*/
          //图案主键
          legend: {
            type: 'scroll',
            orient: 'vertical',
            left: 0,
            top: 20,
            bottom: 20,
            data: this.nameFrom,
            selected: this.nameFrom
          },
          series: [
            {
              name: '数据源',
              type: 'pie',
              radius: '55%',
              center: ['50%', '50%'],
              data:  this.seriesData.sort(function (a, b) { return a.value - b.value }),
              //设置指示线
              /*label: {
                normal: {
                  textStyle: {
                    color: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              },
              labelLine: {
                normal: {
                  lineStyle: {
                    color: 'rgba(255, 255, 255, 0.3)'
                  },
                  smooth: 0.2,
                  length: 10,
                  length2: 20
                }
              },*/
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              animationType: 'scale',
              animationEasing: 'elasticOut',
              animationDelay: function (idx) {
                return Math.random() * 200
              }
            }
          ]
        }

        this.chartPie = echarts.init(document.getElementById('J_chartPieBox'))
        this.chartPie.setOption(option)
        window.addEventListener('resize', () => {
          this.chartPie.resize()
        })
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
</style>
