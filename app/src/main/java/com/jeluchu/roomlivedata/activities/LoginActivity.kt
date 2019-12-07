package com.jeluchu.roomlivedata.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonElement
import com.jeluchu.roomlivedata.Application
import com.jeluchu.roomlivedata.R
import com.jeluchu.roomlivedata.utils.*
import com.jeluchu.roomlivedata.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    var sharedPreferenceHelper: SharedPreferenceHelper? = null


    var viewModelFactory: ViewModelFactory? = null
        @Inject set

    lateinit var viewModel: LoginViewModel

    lateinit var progressDialog: ProgressDialog

    /*
     * method to validate $(mobile number) and $(password)
     * */
    private val isValid: Boolean
        get() {

            if (email?.text.toString().length <= 0) {
                Toast.makeText(
                    this@LoginActivity,
                    resources.getString(R.string.enter_mobile_number),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (password?.text.toString().length <= 0) {
                Toast.makeText(
                    this@LoginActivity,
                    resources.getString(R.string.enter_valid_password),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

            return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)



        sharedPreferenceHelper = SharedPreferenceHelper.instance


        if (sharedPreferenceHelper!!.getBoolean(Constants.Theme)) {
            setTheme(R.style.darkTheme)

        } else {
            setTheme(R.style.AppTheme)
        }

        progressDialog = Constants.getProgressDialog(this, "Please wait...")


        (application as Application).appComponent.doInjectionLogin(this)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(LoginViewModel::class.java)

        viewModel.loginResponse().observe(this, Observer<ApiResponse> {
            if (it != null) {
                this.consumeResponse(it)
            }
        })
        getSupportActionBar()!!.hide()
        email.setText("252525@sdfgdrg.hk")
        password.setText("Accountusername99999")

        login.setOnClickListener { onLoginClicked() }


    }


    // @OnClick(R.id.login)
    fun onLoginClicked() {
        if (isValid) {
            if (!Constants.checkInternetConnection(this)) {
                Toast.makeText(
                    this@LoginActivity,
                    resources.getString(R.string.network_error),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.hitLoginApi(
                    "0aPrw1k5acAj4gFcic7DsmhC4e0POftD",
                    email?.text.toString(),
                    password?.text.toString()
                )
            }

        }
    }


    /*
     * method to handle response
     * */
    private fun consumeResponse(apiResponse: ApiResponse) {

        when (apiResponse.status) {

            Status.LOADING -> progressDialog.show()

            Status.SUCCESS -> {
                progressDialog.dismiss()


                //    apiResponse.data?.let {

                if (renderSuccessResponse(apiResponse.data!!.asJsonObject)) {
                    startActivity(Intent(this@LoginActivity, DietActivity::class.java))
                    Log.d("LoginActivity", "Result is OKay")
                }
                //   }
            }

            Status.ERROR -> {
                progressDialog.dismiss()
                println("ActiveAccountResponse" + resources.getString(R.string.errorString) + apiResponse)
                Toast.makeText(
                    this@LoginActivity,
                    resources.getString(R.string.errorString) + apiResponse,
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }

    /*
     * method to handle success response
     * */
    private fun renderSuccessResponse(response: JsonElement): Boolean {
        if (!response.isJsonNull) {


            if (!response.asJsonObject.get("data").isJsonNull) {

                if (response.asJsonObject.get("data").asBoolean)
                    return true
                return false
            }
            return false


//            Utilities.setPrefValue(
//                Constants.kUSER_ID,
//                response.asJsonObject.get("result").asJsonObject.get("admin").asJsonObject.get("id").toString(),
//                this
//            )
//            Utilities.setPrefValue(
//                Constants.kPARENT_ID,
//                response.asJsonObject.get("result").asJsonObject.get("admin").asJsonObject.get("parent_id").toString(),
//                this
//            )

            //   startActivity(Intent(this@LoginActivity, SelectActivity::class.java))

        }
//        else {
//            Toast.makeText(
//                this@LoginActivity,
//                resources.getString(R.string.errorString),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
        return false
    }

}
