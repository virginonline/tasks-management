package com.github.virginonline.tasks.domain.comment;

import com.github.virginonline.tasks.domain.AbstractEntity;
import com.github.virginonline.tasks.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "comments")
@EqualsAndHashCode(callSuper = true)
public class Comment extends AbstractEntity {
  @Column(name = "content", nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User author;
}
