package com.gencal.testmod.message;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class MessageService {

    public void saveMessage(String uuidString, String text) {
        UUID uuid = UUID.fromString(uuidString);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            MessageEntity message = new MessageEntity(uuid, text);  // Pass UUID object
            session.persist(message);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public MessageEntity findMessageByUuid(String uuid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                            "FROM MessageEntity WHERE uuid = :uuid",
                            MessageEntity.class
                    )
                    .setParameter("uuid", uuid)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }
}