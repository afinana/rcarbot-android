package com.middleand.mobile.rccar.framents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.middleand.mobile.rccar.R;
import com.middleand.mobile.rccar.framents.JoystickView.OnJoystickMoveListener;
import com.middleand.mobile.rccar.logger.Log;

public class JoystickFragment extends Fragment {

    protected static final String TAG = "JoystickFragment";
    private TextView angleTextView = null;
    private TextView powerTextView = null;
    private TextView directionTextView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joystick, container,
                false);

        @SuppressWarnings("unused")
        LinearLayout relativeLayout = (LinearLayout) rootView
                .findViewById(R.id.joystick_layout);

        angleTextView = (TextView) rootView.findViewById(R.id.angleTextView);
        powerTextView = (TextView) rootView.findViewById(R.id.powerTextView);
        directionTextView = (TextView) rootView.findViewById(R.id.directionTextView);

        // Referenciando igualmente a otras views
        //JoystickView joystickView = new JoystickView(getActivity());

        JoystickView joystickView = (JoystickView) rootView.findViewById(R.id.joystickView);


        // Listener de eventos que sempre retornar� com a varia��o do angulo em
        // graus, a for�a do movimento em porcentagem e a dire��o do movimento
        joystickView.setOnJoystickMoveListener(new OnJoystickMoveListener() {
            private String btCommand;

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                angleTextView.setText(" " + String.valueOf(angle) + "�");
                powerTextView.setText(" " + String.valueOf(power) + "%");
                btCommand = angle + ":" + power;

                Log.d(TAG, "BT command:" + btCommand);
                switch (direction) {
                    case JoystickView.FRONT:
                        directionTextView.setText(R.string.front);
                        break;

                    case JoystickView.FRONT_RIGHT:
                        directionTextView.setText(R.string.front_right);
                        break;

                    case JoystickView.RIGHT:
                        directionTextView.setText(R.string.right);
                        break;

                    case JoystickView.BOTTOM_RIGHT:
                        directionTextView.setText(R.string.bottom_right);
                        break;

                    case JoystickView.BOTTOM:
                        directionTextView.setText(R.string.bottom);
                        break;

                    case JoystickView.BOTTOM_LEFT:
                        directionTextView.setText(R.string.bottom_left);
                        break;

                    case JoystickView.LEFT:
                        directionTextView.setText(R.string.left);
                        break;

                    case JoystickView.FRONT_LEFT:
                        directionTextView.setText(R.string.front_left);
                        break;

                    default:
                        directionTextView.setText(R.string.center);
                }
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);
        //relativeLayout.addView(joystickView);

        return rootView;
    }

}
