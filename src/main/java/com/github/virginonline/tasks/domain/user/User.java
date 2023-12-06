package com.github.virginonline.tasks.domain.user;

import com.github.virginonline.tasks.domain.task.Task;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @NotEmpty
  @Column(name = "username", unique = true)
  private String username;

  @Email
  @NotEmpty
  @Column(name = "email", unique = true)
  private String email;

  @NotEmpty
  private String password;

  @Column(name = "role")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "users_roles")
  @Enumerated(value = EnumType.STRING)
  private Set<Role> roles;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_tasks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
  private List<Task> ownedTasks;


  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "assigned_tasks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
  private List<Task> assignedTasks;


  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;


}
