package duksung.android.hororok.ugeubi.retrofit.Login;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("userId")
    String userId;

    @SerializedName("userName")
    String userName;

    @SerializedName("email")
    String email;
}