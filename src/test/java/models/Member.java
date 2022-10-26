package models;

/** Make sure that fields and properties of this class should exactly match with the request or response body
 * whichever is the superset.
 * For example in our case, when we make POST request we provide two fields i.e. name and gender but when we do GET
 * request we get three fields as response i.e. id, name and gender. bcz id is auto-populated by the system.
 * Even if we send "id" with POST request , it doesnt get processed and sends back 400 error. So in our case response
 * body is superset.
 */

import com.google.gson.annotations.Expose;

/** Beans : Beans are special types of POJO. There are some restrictions a POJO to be a bean.
 * If we add a no argument constructor in this class than it would become a bean.
 * Please note that all Java beans are POJO but not all POJOs are Java Beans.
 */
public class Member {

    /**
     * Making id as transient bcz we cant leave or set "id" in this case as when we set id it throws error
     * as we can only set name and gender. and if we leave it then "id" will be set to 0 and we'll get another error msg.
     * To solve this , we have two method one is by using transient keyword and another is by using Json Library.
     * We made "id" as transient. Transient is a key to indicate that a field should not be part of the serialization process.
     * So whenever we use transient keyword with any field it means we excludes teh serialization of that particular field.
     */

  // private transient int id;
    //    private  String name;
    //    private  String gender;



    /** In GSon Library whichever fields we want to expose for serialization , we'll mention those fields explicilty.
     * To do this , create a field once and use an annotation "@Expose()" It comes from google. gson. annotations.
     * Repeat above process with all the fields which you want to expose for serialization.
     * The fields that you don't want to serialize mentions the state along with @Expose annotation example,
     * @Expose(serialize = false ), This will exclude that particular fields from being serialized.
     * And if you have created same POJO class for response body and you want to deserialize that field in response
     * than mention @Expose (serialize-= false, deserialize = true) above that field. This will allow to deserialze that
     * field in response.
     */
    @Expose(serialize = false, deserialize = true)
    private int id;

    @Expose
    private String name;

    @Expose
    private String gender;

    public Member(String name, String gender) {
        super();
        this.name = name;
        this.gender = gender;
    }

    /** Creating getters and setter for all fields.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**Two String Method is useful in Debugging.
     */

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
