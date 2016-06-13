package igcsoftware.bobsaddressbook;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by BobSSD on 6/11/2016.
 */
public class AddressEditDialogFragment extends DialogFragment implements View.OnClickListener {
    private static AddressCard card;
    private static onDismissListener onDismissListener;

    Button saveButton;
    Button deleteButton;
    Button addButton;

    EditText contactNameET;
    EditText addressET;
    EditText address2ET;
    EditText cityET;
    EditText stateET;
    EditText zipET;

    public static AddressEditDialogFragment newInstance(onDismissListener odl) {
        onDismissListener = odl;
        return new AddressEditDialogFragment();
    }

    public static AddressEditDialogFragment newInstance(AddressCard currentCard, onDismissListener odl) {
        onDismissListener = odl;
        card = currentCard;
        return new AddressEditDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        final Window window = dialog.getWindow();

        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.address_edit_dialog_fragment, container, false);
        setupUI(v);
        return v;
    }

    private void setupUI(View v) {
        saveButton = (Button) v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        deleteButton = (Button) v.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        addButton = (Button) v.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        contactNameET = (EditText) v.findViewById(R.id.contact_name_et);
        addressET = (EditText) v.findViewById(R.id.address_et);
        address2ET = (EditText) v.findViewById(R.id.address2_et);
        cityET = (EditText) v.findViewById(R.id.city_et);
        stateET = (EditText) v.findViewById(R.id.state_et);
        zipET = (EditText) v.findViewById(R.id.zip_et);

        if (this.getTag().equals("addAddress")) {
            deleteButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
        } else {
            populateFields(card);
        }
    }

    private void populateFields(AddressCard card) {
        contactNameET.setText(card.getContactName());
        addressET.setText(card.getAddress());
        address2ET.setText(card.getAddress2());
        cityET.setText(card.getCity());
        stateET.setText(card.getState());
        zipET.setText(card.getZip());

    }

    private void insertCard(Context context) {
        String add2 = address2ET.getText().toString();
        if (add2.isEmpty() || add2.equals(""))
            add2 = null;


        String contactName = contactNameET.getText().toString();
        String address = addressET.getText().toString();
        String address2 = add2;
        String city = cityET.getText().toString();
        String state = stateET.getText().toString();
        String zip = zipET.getText().toString();

        AddressCard newCard = new AddressCard(contactName, address, address2, city, state, zip);

        AddressDao.insertCard(context, newCard);
        onDismissListener.onDismiss();

    }

    private void updateCard(Context context) {
        String add2 = address2ET.getText().toString();
        if (add2.isEmpty() || add2.equals(""))
            add2 = null;

        String contactName = contactNameET.getText().toString();
        String address = addressET.getText().toString();
        String address2 = add2;
        String city = cityET.getText().toString();
        String state = stateET.getText().toString();
        String zip = zipET.getText().toString();

        AddressCard updateCard = new AddressCard(contactName, address, address2, city, state, zip);

        AddressDao.updateCard(context, updateCard, card);
        onDismissListener.onDismiss();
    }

    private void deleteCard(Context context) {
        String contactName = contactNameET.getText().toString();
        String address = addressET.getText().toString();

        AddressDao.deleteCard(context, contactName, address);
        onDismissListener.onDismiss();

    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        String name = contactNameET.getText().toString();
        String message = "has not changed";

        if (validateFields(context)) {
            switch (v.getId()) {
                case R.id.save_button:
                    updateCard(context);
                    message = "has been updated!";
                    break;
                // normally wouldn't need to check fields here- but card exists, so all fields exist
                case R.id.delete_button:
                    deleteCard(context);
                    message = "has been deleted!";
                    break;
                case R.id.add_button:
                    insertCard(context);
                    message = "has been added!";
                    break;
                default:
                    break;
            }
            Toast.makeText(context, name + " " + message, Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }

    private boolean validateFields(Context context) {
        String contactName = contactNameET.getText().toString();
        String address = addressET.getText().toString();
        String city = cityET.getText().toString();
        String state = stateET.getText().toString();
        String zip = zipET.getText().toString();

        String errorMessage = null;

        if (contactName.isEmpty() || contactName.equals(""))
            errorMessage = "A contact name is required.";
        else if (address.isEmpty() || address.equals(""))
            errorMessage = "An address is required.";
        else if (city.isEmpty() || city.equals(""))
            errorMessage = "A city is required.";
        else if (state.isEmpty() || state.equals(""))
            errorMessage = "A state is required.";
        else if (zip.isEmpty() || zip.equals(""))
            errorMessage = "A zip code is required.";

        boolean hasError = errorMessage != null;

        if (hasError) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
        return !hasError;
    }

    public interface onDismissListener {
        void onDismiss();
    }
}

