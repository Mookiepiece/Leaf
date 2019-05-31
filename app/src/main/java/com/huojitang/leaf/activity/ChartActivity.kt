package com.huojitang.leaf.activity

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import com.huojitang.leaf.R
import com.huojitang.leaf.dao.MonthlyBillDao
import com.huojitang.leaf.dao.TagDao
import com.huojitang.leaf.model.BillItem
import com.huojitang.leaf.model.MonthlyBill
import com.huojitang.leaf.model.Tag
import com.huojitang.leaf.util.LeafDateSupport
import java.time.YearMonth

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
    private lateinit var selectedYearMonth: YearMonth
    private lateinit var currentMonthlyBill: MonthlyBill

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        // 绑定各组件
        lineChart = findViewById(R.id.lineChart)
        pieChart = findViewById(R.id.pieChart)
        prevMonthButton = findViewById(R.id.prevMonthButton)
        nextMonthButton = findViewById(R.id.nextMonthButton)
        currentMonthTextView = findViewById(R.id.currentMonthTextView)

        selectedYearMonth = YearMonth.now()
        currentMonthTextView.text = String.format(
                resources.getString(R.string.current_month_text_view_text), selectedYearMonth
        )

        setupLineChart()
        setupPieChart()

        currentMonthlyBill = MonthlyBillDao.getInstance().getByDate(selectedYearMonth.toString())

        setLineChartData()
        setPieChartData()
    }

    private fun setLineChartData() {
        fillLineChart(fetchLineChartData())
    }

    private fun setPieChartData() {
        fillPieChart(fetchPieChartData())
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
        selectedYearMonth = LeafDateSupport.prevMonth(selectedYearMonth)
        currentMonthTextView.text = String.format(
                resources.getString(R.string.current_month_text_view_text), selectedYearMonth
        )
        setLineChartData()
        setPieChartData()
    }

    /**
     * 当“下一月”按钮被点击时的响应事件
     */
    fun jumpToNextMonth(view: View) {
        selectedYearMonth = LeafDateSupport.nextMonth(selectedYearMonth)
        currentMonthTextView.text = String.format(
                resources.getString(R.string.current_month_text_view_text), selectedYearMonth
        )
        setLineChartData()
        setPieChartData()
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

    private fun fetchBillItems(): List<BillItem> {
        return currentMonthlyBill.billItems
    }

    private fun fetchPieChartData(): ArrayList<Pair<Tag, Int>> {
        val data = ArrayList<Pair<Tag, Int>>()
        val allTags = TagDao.getInstance().listAll()
        for (tag in allTags) {
            val consumption = TagDao.getInstance().getConsumption(tag, currentMonthlyBill)
            data.add(Pair(tag, consumption))
        }
        return data
    }

    /**
     * 获取 LineChart 的数据（统计每日消费额数据）
     * @return 一个 FloatArray，其长度为当月的长度，第 i 项表示当月第 i + 1 日的消费额
     */
    private fun fetchLineChartData(): FloatArray {
        val result = FloatArray(selectedYearMonth.lengthOfMonth())
        val billItems = fetchBillItems()
        for (item in billItems) {
            result[item.day - 1] += (item.value / 100.0f)
        }
        return result
    }

    private fun fillLineChart(data: FloatArray) {
        val lineChartEntries = ArrayList<Entry>(data.size)
        for (i in 0 until data.size) {
            lineChartEntries.add(Entry((i + 1).toFloat(), data[i]))
        }

        val lineDataSet: LineDataSet
        if (lineChart.data != null && lineChart.data.dataSetCount > 0) {
            lineDataSet = lineChart.data.getDataSetByIndex(0) as LineDataSet
            lineDataSet.values = lineChartEntries
        } else {
            lineDataSet = LineDataSet(lineChartEntries, "日均消费详情")
            lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            lineDataSet.setDrawIcons(false)
            lineDataSet.enableDashedLine(10f, 5f, 0f)

            lineDataSet.color = Color.BLACK
            lineDataSet.setCircleColor(Color.BLACK)

            lineDataSet.lineWidth = 1f
            lineDataSet.circleRadius = 2f

            lineDataSet.setDrawCircleHole(false)

            lineDataSet.formLineWidth = 1f
            lineDataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            lineDataSet.formSize = 15f

            lineDataSet.valueTextSize = 9f

            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f)
            lineDataSet.setDrawFilled(true)
            lineDataSet.fillFormatter = IFillFormatter { _, _ -> lineChart.axisLeft.axisMinimum }
        }

        val dataSets = arrayListOf<ILineDataSet>(lineDataSet)

        lineChart.data = LineData(dataSets)
        lineChart.invalidate()
    }

    private fun fillPieChart(data: ArrayList<Pair<Tag, Int>>) {
        val pieEntries = ArrayList<PieEntry>(data.size)
        val colors = ArrayList<Int>(data.size)

        for (pair in data) {
            pieEntries.add(PieEntry(pair.second / 100.0f, pair.first.name))
            colors.add(pair.first.color)
        }

        val dataSet = PieDataSet(pieEntries, "详情")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset= MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.colors = colors

        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(14f)
        pieData.setValueTextColor(Color.WHITE)

        pieChart.data = pieData
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }
}
