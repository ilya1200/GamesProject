package Ilya.Project.GamesProject.utils.handlers;


import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.rest.ErrorMessage;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ErrorMessageHandler {
    public static String getErrorMessageFromResponse(Response response) {
        Context context = ContextProvider.getApplicationContext();
        String message = "";
        try (ResponseBody errorBody = response.errorBody()) {
            if (errorBody != null) {
                ErrorMessage errorMessage = new Gson().fromJson(errorBody.string(), ErrorMessage.class);
                try {
                    int errorMessageIdentifier =
                            context.getResources().getIdentifier(
                                    "error_message_" + errorMessage.getMessageID(), "string", context.getPackageName());
                    message = context.getString(errorMessageIdentifier);
                } catch (Exception e) {
                    message = errorMessage.getMessage();
                }
            } else {
                return context.getString(R.string.request_failed);
            }
        } catch (IOException e) {
            return context.getString(R.string.request_failed);
        }
        return message;
    }
}
