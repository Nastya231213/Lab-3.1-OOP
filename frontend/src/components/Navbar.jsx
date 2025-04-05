import { useAuth0 } from "@auth0/auth0-react";
import { Link } from "react-router-dom";
import LoginButton from "./LoginButton";
import LogoutButton from "./LogoutButton";
import AuthSyncer from "./AuthSyncer";

const Navbar = () => {
  const { user, isAuthenticated } = useAuth0();

  const isAdmin = user?.email === "nastya1200526@gmail.com";

  return (
    <>
      <AuthSyncer />

      <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
        <div className="container">
          <Link className="navbar-brand" to="/">TourAgency</Link>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link active" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/tours">Tours</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/pricing">Pricing</Link>
              </li>
              <li className="nav-item">
                <span className="nav-link disabled" tabIndex={-1} aria-disabled="true">Disabled</span>
              </li>

              {isAdmin && (
                <li className="nav-item">
                  <Link className="nav-link" to="/admin">Admin Panel</Link>
                </li>
              )}
            </ul>
          </div>

          <div>
            {isAuthenticated ? <LogoutButton /> : <LoginButton />}
          </div>
        </div>
      </nav>
    </>
  );
};

export default Navbar;
