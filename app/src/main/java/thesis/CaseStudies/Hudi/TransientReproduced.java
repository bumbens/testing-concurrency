package thesis.CaseStudies.Hudi;


public class TransientReproduced {
    
    private Object ref;              
    private boolean initialized;    

    public Object get() {
            if (!initialized) {
                synchronized (this) {
                    if (!initialized) {
                        ref = new Object();
                        initialized = true;
                    }
                }
            }
            return ref;
        }

        public void reset() {
            synchronized (this) {
                ref = null;
                initialized = false;
            }
        }


    public static class TransientReproducedFixed {
        private Object ref;              
        private boolean initialized;    


        public synchronized Object get() {
            if (!initialized) {
                ref = new Object();
                initialized = true;
            }
            return ref;
        }

        public void reset() {
            synchronized (this) {
                ref = null;
                initialized = false;
            }
        }
        
    }
}