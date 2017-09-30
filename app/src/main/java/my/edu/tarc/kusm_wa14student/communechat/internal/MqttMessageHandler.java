package my.edu.tarc.kusm_wa14student.communechat.internal;

import java.util.ArrayList;

import my.edu.tarc.kusm_wa14student.communechat.model.Contact;
import my.edu.tarc.kusm_wa14student.communechat.model.MqttCommand;

/**
 * Created by Xeosz on 27-Sep-17.
 */

public class MqttMessageHandler {
    private final static String RESERVED_STRING = "000000000000000000000000";
    //Static Variables
    private static String REQ_CONTACT_LIST = "003810";
    private static String ACK_CONTACT_LIST = "003811";
    private static String REQ_CONTACT_DETAILS = "003812";
    private static String ACK_CONTACT_DETAILS = "003813";
    private static String REQ_USER_PROFILE = "003814";
    private static String ACK_USER_PROFILE = "003815";
    private static String REQ_SEARCH_USER = "003816";
    private static String ACK_SEARCH_USER = "003817";
    private static String KEEP_ALIVE = "003999";
    public MqttCommand mqttCommand;
    private String publish;
    private String received;
    //FORMAT

    //Command Range 003801 - 004000
    //Reserved 24Bytes / 12Chars
    public MqttMessageHandler() {
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
        this.decode();
    }

    public void encode(MqttCommand command, Object data) {
        StringBuilder sb = new StringBuilder();
        String result = null;
        switch (command) {
            case REQ_CONTACT_LIST: {
                String uid;
                uid = (String) data;
                sb.append(REQ_CONTACT_LIST
                        + RESERVED_STRING
                        + uid.toString());
                result = sb.toString();
                break;
            }
            case ACK_CONTACT_LIST: {
                String message;
                message = (String) publish;
                break;
            }
            case REQ_CONTACT_DETAILS: {
                break;
            }
            case ACK_CONTACT_DETAILS: {
                break;
            }
            case REQ_USER_PROFILE: {
                break;
            }
            case ACK_USER_PROFILE: {
                break;
            }
            case REQ_SEARCH_USER: {
                break;
            }
            case ACK_SEARCH_USER: {
                break;
            }
            case KEEP_ALIVE: {
                break;
            }
        }
        this.publish = result;
    }


    private void decode() {
        String command = received.substring(0, 30);
        switch (command) {
            case "003811" + RESERVED_STRING: {
                this.mqttCommand = MqttCommand.ACK_CONTACT_LIST;
                break;
            }
            default:
                this.mqttCommand = null;
        }
    }

    public ArrayList<Contact> getContactList() {
        if (mqttCommand == MqttCommand.ACK_CONTACT_LIST) {
            received = received.substring(30);
            ArrayList<Contact> contacts = new ArrayList<>();
            int temp = 0;
            String data = received;
            // Data structure sequence
            // id / gender / last online / sizeof / username / sizeof / nickname / sizeof / status / sizeof / phone number
            while (!data.isEmpty()) {
                Contact contact = new Contact();

                contact.setUid(Integer.parseInt(data.substring(0, 10)));
                data = data.substring(10);

                contact.setGender(Integer.parseInt(data.substring(0, 1)));
                data = data.substring(1);

                contact.setLast_online(Integer.parseInt(data.substring(0, 10)));
                data = data.substring(10);

                temp = Integer.parseInt(data.substring(0, 3));
                data = data.substring(3);
                contact.setUsername(data.substring(0, temp));
                data = data.substring(temp);

                temp = Integer.parseInt(data.substring(0, 3));
                data = data.substring(3);
                contact.setNickname(data.substring(0, temp));
                data = data.substring(temp);

                temp = Integer.parseInt(data.substring(0, 3));
                data = data.substring(3);
                contact.setStatus(data.substring(0, temp));
                data = data.substring(temp);

                temp = Integer.parseInt(data.substring(0, 3));
                data = data.substring(3);
                contact.setPhone_number(data.substring(0, temp));
                data = data.substring(temp);

                contacts.add(contact);
            }
            return contacts;
        }
        return null;
    }

    public boolean isReceiving() {
        return (this.mqttCommand == MqttCommand.ACK_CONTACT_LIST ||
                this.mqttCommand == MqttCommand.ACK_SEARCH_USER ||
                this.mqttCommand == MqttCommand.ACK_CONTACT_DETAILS ||
                this.mqttCommand == MqttCommand.ACK_USER_PROFILE);
    }

}