package app.zingo.employeemanagements.UI.Landing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import app.zingo.employeemanagements.Custom.CustomFontTextView;
import app.zingo.employeemanagements.R;
import app.zingo.employeemanagements.UI.Company.CreateCompany;
import app.zingo.employeemanagements.Utils.PreferenceHandler;
import okhttp3.internal.Util;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneVerificationScreen extends AppCompatActivity {

    LinearLayout mNumberLay,mOtpLay;
    CountryCodePicker mCountry;
    TextInputEditText mPhone,mOtp;
    AppCompatButton mVerifyNum,mVerifyCode;
    CustomFontTextView mMobileNumWithCountry;
    AppCompatTextView mCancel,mResend,mTimer;

    //Firebase & Auth
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private FirebaseAuth mAuth;
    private static final String TAG = "GuestLoginScreen";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    MyCountDownTimer myCountDownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_phone_verification_screen);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            mNumberLay = (LinearLayout)findViewById(R.id.number_layout);
            mOtpLay = (LinearLayout)findViewById(R.id.otp_layout);

            mCountry = (CountryCodePicker)findViewById(R.id.ccp);

            mPhone = (TextInputEditText)findViewById(R.id.phone);
            mOtp = (TextInputEditText)findViewById(R.id.otp);

            mVerifyNum = (AppCompatButton)findViewById(R.id.verify_number);
            mVerifyCode = (AppCompatButton)findViewById(R.id.verify_code);

            mMobileNumWithCountry = (CustomFontTextView)findViewById(R.id.mobile_number_text);

            mCancel = (AppCompatTextView)findViewById(R.id.cancel);
            mResend = (AppCompatTextView)findViewById(R.id.resend);
            mTimer = (AppCompatTextView)findViewById(R.id.timer);

            mVerifyNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String phoneNumber = mPhone.getText().toString();
                    if(phoneNumber==null||phoneNumber.isEmpty()){
                        mPhone.setError("Please enter mobile number");
                        mPhone.requestFocus();
                    }else{

                        mNumberLay.setVisibility(View.GONE);
                        mOtpLay.setVisibility(View.VISIBLE);
                        mResend.setVisibility(View.GONE);
                        mTimer.setVisibility(View.VISIBLE);

                        myCountDownTimer = new MyCountDownTimer(10000, 1000);
                        myCountDownTimer.start();
                        mMobileNumWithCountry.setText(""+mCountry.getSelectedCountryCodeWithPlus()+mPhone.getText().toString());
                        startPhoneNumberVerification(mCountry.getSelectedCountryCodeWithPlus()+mPhone.getText().toString());



                    }
                }
            });

            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    mOtpLay.setVisibility(View.GONE);
                    mNumberLay.setVisibility(View.VISIBLE);
                    mPhone.setText("");
                }
            });

            mAuth = FirebaseAuth.getInstance();
            // [END initialize_auth]
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {

                    Log.d(TAG, "onVerificationCompleted:" + credential);

                    mVerificationInProgress = false;

                    String code = credential.getSmsCode();
                    mOtp.setText(code);

                    System.out.println("Code = "+code);

                    signInWithPhoneAuthCredential(credential);


                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    mVerificationInProgress = false;

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        mPhone.setError("Invalid phone number.");
                    } else if (e instanceof FirebaseTooManyRequestsException) {

                        Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.", Snackbar.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                    Log.d(TAG, "onCodeSent:" + verificationId);


                    mVerificationId = verificationId;
                    mResendToken = token;
                    Toast.makeText(PhoneVerificationScreen.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();

                }
            };

            mVerifyCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOtp.getText().toString().isEmpty()){
                        mOtp.setError("Please enter otp");
                    }else{
                        verifyPhoneNumberWithCode(mVerificationId,mOtp.getText().toString());
                    }

                }
            });

            mResend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resendVerificationCode(mCountry.getSelectedCountryCodeWithPlus()+mPhone.getText().toString(),mResendToken);
                }
            });


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //Firebase function
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

        mVerificationInProgress = true;

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() ) {

                            PreferenceHandler.getInstance(PhoneVerificationScreen.this).setPhoneNumber(mPhone.getText().toString());
                            Intent main = new Intent(PhoneVerificationScreen.this, CreateCompany.class);
                            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            main.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(main);
                            PhoneVerificationScreen.this.finish();


                        } else {

                            Log.w("LoginActivity", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                mOtp.setError("Invalid code.");

                            }

                        }
                    }
                });
    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);

            mTimer.setText("00:"+String.format("%02d", progress));
        }

        @Override
        public void onFinish() {
            mTimer.setVisibility(View.GONE);
            mResend.setVisibility(View.VISIBLE);
        }
    }

}
