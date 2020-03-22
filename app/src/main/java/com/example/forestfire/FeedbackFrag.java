package com.example.forestfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FeedbackFrag extends Fragment implements View.OnClickListener {

    private EditText mEditTextMessage;
    private String Email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_feedback, container, false);

        assert container != null;
        Email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        mEditTextMessage = v.findViewById(R.id.feedback_message);

        Button bSend = v.findViewById(R.id.btn_send);
        bSend.setOnClickListener(this);
        return v;
    }

    private void sendMail() {
        String recipientList = "2016.tarun.dadlani@ves.ac.in, 2016.pranjali.tembhurnikar@ves.ac.in, 2016.ashutosh.matai@ves.ac.in, 2016.richa.kalani@ves.ac.in";
        String[] recipients = recipientList.split(",");

        String subject = "[Feedback] " + Email;
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(intent, "Send email with...?"));
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(FeedbackFrag.this.getActivity(), "No email clients installed on device!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        sendMail();
    }
}
