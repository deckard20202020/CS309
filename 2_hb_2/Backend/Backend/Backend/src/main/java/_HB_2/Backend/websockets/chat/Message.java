package _HB_2.Backend.websockets.chat;

import java.util.Date;

import javax.persistence.*;

import _HB_2.Backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User userSent;

    @JsonIgnore
    @ManyToOne
    private User userReceived;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();


    public Message() {};

    public Message(User userName, String content) {
        this.userSent = userName;
        this.content = content;
        this.userReceived = null;
    }

    public Message(User userSent, User userReceived, String content) {
        this.userSent = userSent;
        this.content = content;
        this.userReceived = userReceived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserSent() {
        return userSent;
    }

    public void setUserSent(User userName) {
        this.userSent = userName;
    }

    public User getUserReceived() {
        return userReceived;
    }

    public void setUserReceived(User userName) {
        this.userReceived = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }


}
