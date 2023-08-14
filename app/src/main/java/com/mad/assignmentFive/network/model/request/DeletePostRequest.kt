package com.mad.assignmentFive.network.model.request

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class DeletePostRequest(
    var userId: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeletePostRequest> {
        override fun createFromParcel(parcel: Parcel): DeletePostRequest {
            return DeletePostRequest(parcel)
        }

        override fun newArray(size: Int): Array<DeletePostRequest?> {
            return arrayOfNulls(size)
        }
    }
}
