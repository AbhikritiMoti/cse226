package com.example.wevote

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie

class CampaignDetailsActivity : AppCompatActivity() {
    private lateinit var pieChartView: AnyChartView
    private lateinit var pie: Pie // Reference to the pie chart
    private lateinit var option1CountTextView: TextView
    private lateinit var option2CountTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campaign_details)
        supportActionBar?.hide()

        val id: TextView = findViewById(R.id.textView2)
        val subjectTextView: TextView = findViewById(R.id.subject)
        val option1NameTextView: TextView = findViewById(R.id.option1NameTextView)
        option1CountTextView = findViewById(R.id.option1CountTextView) // Declare here as a class-level property
        val option2NameTextView: TextView = findViewById(R.id.option2NameTextView)
        option2CountTextView = findViewById(R.id.option2CountTextView) // Declare here as a class-level property

        val option1CountIncrementButton: Button = findViewById(R.id.option1CountIncrement)
        val option2CountIncrementButton: Button = findViewById(R.id.option2CountIncrement)


        pieChartView = findViewById(R.id.piechart)

        val font = Typeface.createFromAsset(assets, "batmfa.ttf")
        id.typeface = font
        option1NameTextView.typeface = font
        option2NameTextView.typeface = font
        option1CountTextView.typeface = font
        option2CountTextView.typeface = font

        val font2 = Typeface.createFromAsset(assets, "coolvetica.otf")
        subjectTextView.typeface = font



        val campaignId = intent.getIntExtra("campaignId", -1) // -1 is the default value if the extra is not found
        Log.d("CampaignDetailsActivity", "campaignId: $campaignId")
        if (campaignId != -1) {
            val databaseHandler = DatabaseHandler(this)
            val campaignDetails = databaseHandler.getCampaignDetailsById(campaignId)

            if (campaignDetails != null) {
                subjectTextView.text = campaignDetails.subject.toString()
            }
            if (campaignDetails != null) {
                option1NameTextView.text = campaignDetails.opt1name.toString()
            }
            if (campaignDetails != null) {
                option2NameTextView.text = campaignDetails.opt2name.toString()
            }
            if (campaignDetails != null) {
                id.text = "Campaign ID : " + campaignDetails.id.toString();
            }


            val predictButton: Button = findViewById(R.id.predict)
            predictButton.setOnClickListener {
                // Handle predict button click
                // You can update the chart here or call a function to update it
                updatePieChart()
                initializePieChart()

            }



            if (campaignDetails != null) {
                option1CountTextView.text = campaignDetails.opt1count.toString()
                option2CountTextView.text = campaignDetails.opt2count.toString()

                // Handle button clicks
                option1CountIncrementButton.setOnClickListener {
                    // Increase option1 count and update the TextView
                    val currentCount = option1CountTextView.text.toString().toInt()
                    val newCount = currentCount + 1
                    option1CountTextView.text = newCount.toString()

                    // Update the database with the new count
                    val newCampaignDetails = campaignDetails.copy(opt1count = newCount)
                    databaseHandler.updateCampaignDetails(campaignId, newCampaignDetails)

                    // Update the pie chart
                    updatePieChart()

                }

                option2CountIncrementButton.setOnClickListener {
                    // Increase option2 count and update the TextView
                    val currentCount = option2CountTextView.text.toString().toInt()
                    val newCount = currentCount + 1
                    option2CountTextView.text = newCount.toString()

                    // Update the database with the new count
                    val newCampaignDetails = campaignDetails.copy(opt2count = newCount)
                    databaseHandler.updateCampaignDetails(campaignId, newCampaignDetails)

                    // Update the pie chart
                    updatePieChart()

                }
                // Initialize the pie chart
                setupPieChart(campaignDetails.opt1count, campaignDetails.opt2count)
            } else {
                id.text = "Campaign ID not found"
            }
        } else {
            id.text = "Campaign ID not found"
        }
    }
    private fun setupPieChart(option1Count: Int, option2Count: Int) {
        val pie = AnyChart.pie()
        val data = mutableListOf<DataEntry>()

        data.add(value("Candidate 1", option1Count))
        data.add(value("Candidate 2", option2Count))

        pie.data(data)
        pie.title("Vote Distribution")

        val legend = pie.legend()
        legend.enabled(true)
        legend.position("bottom")

        pieChartView.setChart(pie)
    }

    private fun initializePieChart() {
        val option1Count = option1CountTextView.text.toString().toInt()
        val option2Count = option2CountTextView.text.toString().toInt()

        val data = mutableListOf<DataEntry>()
        data.add(value("Option 1", option1Count))
        data.add(value("Option 2", option2Count))

        pie = AnyChart.pie()
        pie!!.data(data)
        pie!!.title("Vote Distribution")

        pieChartView.setChart(pie)
    }

    private fun updatePieChart() {
        val pie = AnyChart.pie()
        val option1Count = option1CountTextView.text.toString().toInt()
        val option2Count = option2CountTextView.text.toString().toInt()

        pie.data(listOf(value("Option 1", option1Count), value("Option 2", option2Count)))
    }

    private fun value(x: String, y: Int): DataEntry {
        return ValueDataEntry(x, y)
    }
}

