package domeniu;

import java.io.Serializable;
import java.util.Objects;

public abstract class Entity implements Serializable {

        private static final long serialVersionUID = 1000L;

        protected int id;

        public Entity(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entity entity = (Entity) o;
            return id == entity.id;
        }

        public int hashCode() {
        return Objects.hash(id);
    }

}

