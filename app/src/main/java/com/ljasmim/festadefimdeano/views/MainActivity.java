package com.ljasmim.festadefimdeano.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ljasmim.festadefimdeano.R;
import com.ljasmim.festadefimdeano.constants.FimDeAnoConstants;
import com.ljasmim.festadefimdeano.util.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        this.mViewHolder.textToday = (TextView) findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = (TextView) findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = (Button) findViewById(R.id.button_confirm);

        this.mViewHolder.buttonConfirm.setOnClickListener(this);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLefttToEndOfYear()),
                getString(R.string.dias));
        this.mViewHolder.textDaysLeft.setText(daysLeft);
    }

    private void verifyPresence() {
        String presenceStatus = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);

        if(presenceStatus.equals("")){
            this.mViewHolder.buttonConfirm.setText(R.string.nao_confirmado);
        } else if (presenceStatus.equals(FimDeAnoConstants.CONFIRMED_WILL_GO)){
            this.mViewHolder.buttonConfirm.setText(R.string.sim);
        } else{
            this.mViewHolder.buttonConfirm.setText(R.string.nao);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        String presenceStatus = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);

        if (id == R.id.button_confirm) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY, presenceStatus);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.verifyPresence();
    }

    private int getDaysLefttToEndOfYear(){
        Calendar calenderToday = Calendar.getInstance();
        int today = calenderToday.get(Calendar.DAY_OF_YEAR);

        Calendar calendarLastDay = Calendar.getInstance();
        int lastDayOfYear = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return lastDayOfYear - today;
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}
