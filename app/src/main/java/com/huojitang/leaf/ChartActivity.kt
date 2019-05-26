package com.huojitang.leaf

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

/**
 * ChartActivity
 * 根据每个月的账单显示条形图（按消费日期（每日一结））和饼图（按标签分类）
 *
 * @author 刘忠燏
 */
class ChartActivity : AppCompatActivity(), OnChartValueSelectedListener {
    private lateinit var lineChart: LineChart
    private lateinit var pieChart: PieChart
    private lateinit var prevMonthButton: Button
    private lateinit var nextMonthButton: Button
    private lateinit var currentMonthTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        // 绑定各组件
        lineChart = findViewById(R.id.lineChart)
        pieChart = findViewById(R.id.pieChart)
        prevMonthButton = findViewById(R.id.prevMonthButton)
        nextMonthButton = findViewById(R.id.nextMonthButton)
        currentMonthTextView = findViewById(R.id.currentMonthTextView)

        setupLineChart()
    }

    /**
     * 设定 PieChart 的相关属性（这些属性无法通过 xml 配置）
     */
    private fun setupPieChart() {
        // 显示百分比而非具体数值
        pieChart.setUsePercentValues(true)

        pieChart.description.isEnabled = true
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.isDrawHoleEnabled = true

        pieChart.setOnChartValueSelectedListener(this)
    }

    /** 设定 LineChart 的相关属性（这些属性似乎无法通过 xml 配置） */
    private fun setupLineChart() {
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)

        // 设置图表的 x 和 y 坐标轴
        val xAxis = lineChart.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f)

        val yAxis = lineChart.axisLeft
        yAxis.enableGridDashedLine(10f, 10f, 10f)
        yAxis.axisMaximum = 100f
        yAxis.axisMinimum = 10f
    }

    /**
     * 用于在 PieChart 的图表项被点击时，在中间生成对应的详情信息
     *
     * @param e 传入的图表项
     * @return 对应的 SpannableString 实体
     */
    private fun generateSpannableString(e: PieEntry): SpannableString {
        val s = SpannableString("${e.label}\n${e.value}")
        // 将 label 部分的文字设置成 2 倍大小
        s.setSpan(RelativeSizeSpan(2.0f), 0, e.label.length, 0)
        // 将 value 部分的文字设置成 1.5 倍大小
        s.setSpan(RelativeSizeSpan(1.5f), e.label.length + 1, s.length, 0)
        return s
    }

    /**
     * 当“上一月”按钮被点击时的响应事件
     */
    fun jumpToPrevMonth(view: View) {
        // TODO: 设置“上一月”按钮的跳转事件
    }

    /**
     * 当“下一月”按钮被点击时的响应事件
     */
    fun jumpToNextMonth(view: View) {
        // TODO: 设置“下一月”按钮的跳转事件
    }

    override fun onNothingSelected() {
        if (pieChart.centerText.isNotEmpty()) {
            pieChart.centerText = ""
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null) {
            return
        }

        if (e is PieEntry) {
            pieChart.centerText = generateSpannableString(e)
        }
    }
}
