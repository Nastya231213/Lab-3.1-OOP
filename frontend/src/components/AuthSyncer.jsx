import { useAuth0 } from "@auth0/auth0-react";
import { useEffect, useRef } from "react";
import axios from "axios";

const AuthSyncer = () => {
  const { isAuthenticated, user } = useAuth0();
  const userSynced = useRef(false);

  useEffect(() => {
    const syncUser = async () => {
      if (isAuthenticated && user && !userSynced.current) {
        if (!user.name || !user.email) {
          console.warn("Користувач без name або email", user);
          return;
        }

        const payload = {
          name: user.name,
          email: user.email,
          is_regular: false,
        };

        try {
          const response = await axios.post("/touragency/clients", payload);
          console.log("✅ Користувач збережений або існує:", response.data);
          userSynced.current = true;
        } catch (error) {
          console.error("❌ Помилка в AuthSyncer:", error);
        }
      }
    };

    syncUser();
  }, [isAuthenticated, user]);

  return null; 
};

export default AuthSyncer;
