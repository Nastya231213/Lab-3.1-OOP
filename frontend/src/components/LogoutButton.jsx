import { useAuth0 } from "@auth0/auth0-react";

const LogoutButton = () => {
  const { logout } = useAuth0();

  return (
    <button
      className="btn btn-outline-light"
      onClick={() =>
        logout({ logoutParams: { returnTo: window.location.origin } })
      }
    >
      Logout
    </button>
  );
};

export default LogoutButton;
