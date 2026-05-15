package ucu.edu.aed.entregables;

import java.util.Objects;

    public class Alumno {
        private int id;
        private String fullName;
        private String email;

        public Alumno(int id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Alumno)) return false;
            Alumno other = (Alumno) o;
            return this.id == other.id;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }
    }
