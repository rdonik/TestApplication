package test.application.grafic.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import test.application.grafic.base.BaseFragment
import test.application.grafic.data.point.Point
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import kotlinx.coroutines.flow.collectLatest
import test.application.grafic.R
import test.application.grafic.support.extensions.launchAndRepeatWithViewLifecycle
import test.application.grafic.databinding.FragmentGraphicBinding
import test.application.grafic.shared.enums.MessageType
import test.application.grafic.support.extensions.columnX
import test.application.grafic.support.extensions.columnY
import test.application.grafic.support.extensions.popBackStack
import test.application.grafic.ui.activity.MainViewModel
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class GraphicFragment : BaseFragment<FragmentGraphicBinding>(FragmentGraphicBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.ivAppbarBack.setOnClickListener {
            popBackStack()
        }
        binding.saveButton.setOnClickListener {
            saveChartAsImage()
        }
    }

    private fun initObservers() {
        launchAndRepeatWithViewLifecycle {
            viewModel.points.collectLatest {
                it?.let {
                    setupChart(it.points)
                    setupTable(it.points)
                }
            }
        }
    }

    private fun setupTable(points: List<Point>) {
        val tableLayout = binding.tabLayout
        points.forEach {
            val row = TableRow(requireContext())
            row.addView(context?.columnX(it.x.toString()))
            row.addView(context?.columnY(it.y.toString()))
            tableLayout.addView(row)
        }
    }


    private fun setupChart(points: ArrayList<Point>) {
        if (points.isEmpty()) {
            showMessage(getString(R.string.no_points), MessageType.ERROR)
            return
        }
        val entries = points.map { Entry(it.x, it.y) }.sortedWith(EntryXComparator())
        val dataSet = LineDataSet(entries, getString(R.string.points)).apply {
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
            color = ContextCompat.getColor(requireContext(), R.color.blue)
            valueTextSize = 8f
            lineWidth = 2f
            setDrawFilled(true)
            valueTextColor
            fillColor = R.color.blue
            fillAlpha = 50
            setDrawCircles(true)
            setCircleColor(R.color.red)
            setDrawCircleHole(true)
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        val lineData = LineData(dataSet as ILineDataSet)

        binding.lineChart.apply {
            data = lineData
            setTouchEnabled(true)
            setPinchZoom(true)
            invalidate() // Refresh chart
        }
    }


    private fun saveChartAsImage() {
        val bitmap = binding.lineChart.chartBitmap
        val file = File(requireContext().getExternalFilesDir(null), "chart.png")
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                showMessage(getString(R.string.saved_success), MessageType.SUCCESS)
            }
        } catch (e: Exception) {
            showMessage(getString(R.string.saved_error), MessageType.ERROR)
        }
    }

}