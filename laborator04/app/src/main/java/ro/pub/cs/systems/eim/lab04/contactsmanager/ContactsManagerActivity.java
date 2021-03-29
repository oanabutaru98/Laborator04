package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button showHideAdditionalFieldsButton;
    private Button saveContactButton;
    private Button cancelButton;

    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText imEditText;

    private LinearLayout additionalFieldsContainer;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.show_hide_additional_fields:
                    if (additionalFieldsContainer.getVisibility() == View.VISIBLE) {
                        // il facem invisible
                        showHideAdditionalFieldsButton.setText("SHOW ADDITIONAL FIELDS");
                        additionalFieldsContainer.setVisibility(View.INVISIBLE);
                    } else {
                        // il facem visible
                        showHideAdditionalFieldsButton.setText("HIDE ADDITIONAL FIELDS");
                        additionalFieldsContainer.setVisibility(View.VISIBLE);
                    }
                    break;

                case R.id.save_button:
                    String name = nameEditText.getText().toString();
                    String phoneNumber = phoneNumberEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String jobTitle = jobTitleEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String website = websiteEditText.getText().toString();
                    String im = imEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null)
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    if (phoneNumber != null)
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
                    if (email != null)
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    if (address != null)
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    if (jobTitle != null)
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    if (company != null)
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);

                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, 2017);
                    break;

                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        showHideAdditionalFieldsButton = (Button) findViewById(R.id.show_hide_additional_fields);
        showHideAdditionalFieldsButton.setOnClickListener(buttonClickListener);

        saveContactButton = (Button) findViewById(R.id.save_button);
        saveContactButton.setOnClickListener(buttonClickListener);

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        phoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        addressEditText = (EditText) findViewById(R.id.address_edit_text);
        jobTitleEditText = (EditText) findViewById(R.id.job_title_edit_text);
        companyEditText = (EditText) findViewById(R.id.company_edit_text);
        websiteEditText = (EditText) findViewById(R.id.website_edit_text);
        imEditText = (EditText) findViewById(R.id.im_edit_text);

        additionalFieldsContainer = (LinearLayout) findViewById(R.id.additional_fields_container);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneNumberEditText.setText(phone);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 2017:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }


}