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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.textToday = (TextView) findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = (TextView) findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = (Button) findViewById(R.id.button_confirm);
        this.mViewHolder.buttonConfirm.setOnClickListener(this);

        this.mSecurityPreferences = new SecurityPreferences(this);

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

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}
