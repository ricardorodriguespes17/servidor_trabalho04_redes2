package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
  private static final List<User> users = new ArrayList<>();

  public void createUser(User user) {
    users.add(user);
  }

  public List<User> getAllUsers() {
    return new ArrayList<>(users);
  }

  public User getUserById(String ip) {
    for (User user : users) {
      if (user.getIp().equals(ip)) {
        return user;
      }
    }
    return null;
  }

  public void updateUser(User updatedUser) {
    for (User user : users) {
      if (user.getIp().equals(updatedUser.getIp())) {
        user = updatedUser;
      }
    }
  }
}
