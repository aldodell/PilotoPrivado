package com.aldodell.pilotoprivado

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.paypal.android.sdk.payments.*
import org.json.JSONException

class RegistroActivity : AppCompatActivity() {

    //lateinit var etCodigoRegistro: EditText
    // lateinit var btProcesarRegistro: Button
    // lateinit var tvCodigoSemilla: TextView
    //lateinit var preferencias: SharedPreferences
    // lateinit var tvMensajeRegistro: TextView
    lateinit var btnPaypal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val registro = Registro(this)

        // etCodigoRegistro = findViewById(R.id.etCodigoRegistro)
        // btProcesarRegistro = findViewById(R.id.btProcesarRegistro)
        // tvCodigoSemilla = findViewById(R.id.tvCodigoSemilla)
        //   preferencias = this.getPreferences(MODE_PRIVATE)
        //  tvCodigoSemilla.text = registro.generador()
        // tvMensajeRegistro = findViewById(R.id.tvMensajeRegistro)
        btnPaypal = findViewById(R.id.btnPaypal)

        /*
        btProcesarRegistro.setOnClickListener {
            if (registro.procesarRegistro(etCodigoRegistro.text.toString().trim())) {
                finish()
            }./.
        }

         */

        //Paypal
        val paypalConfiguration = PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(PAYPAL_CLIENT_ID)

        val intento = Intent(this, PayPalService::class.java)
        intento.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfiguration)
        startService(intento)


        btnPaypal.setOnClickListener {
            val pago = PayPalPayment(
                "10.0".toBigDecimal(),
                "USD",
                "Piloto privado",
                PayPalPayment.PAYMENT_INTENT_SALE
            )
            val intento = Intent(this, PaymentActivity::class.java)
            intento.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfiguration);
            intento.putExtra(PaymentActivity.EXTRA_PAYMENT, pago);
            startActivityForResult(intento, 0);

        }

        // val spanned = HtmlCompat.fromHtml(resources.getString(R.string.mensaje_registro),HtmlCompat.FROM_HTML_MODE_LEGACY)
        //    tvMensajeRegistro.text = spanned


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val confirm: PaymentConfirmation? =
                data?.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)

            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4))
                } catch (e: JSONException) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                    "paymentExample",
                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs."
                )
            }


        }


    }


    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }


}