import { useAuth0 } from "@auth0/auth0-react";
import Navbar from "../components/Navbar";

const HomePage = () => {
    const { user, isAuthenticated } = useAuth0();

    return (
        <>
            <Navbar />

            <div className="container mt-5">
                {isAuthenticated ? (
                    <div className="card shadow-sm">
                        <div className="card-body">
                            <h3 className="card-title">Welcome, {user.name}!</h3>
                            <p className="card-text">Start browsing our hottest tour offers below.</p>
                            <button className="btn btn-primary mt-3">Book a Tour</button>

                        </div>
                    </div>
                ) : (
                    <div className="alert alert-info text-center">
                        <p>Login to view exclusive tour offers and make bookings!</p>
                    </div>
                )}
                <h2 className="text-center mb-4 mt-2">The types of tours</h2>

                <div className="card-group">
                    <div className="card">
                        <img
                            src="https://beachables.com/cdn/shop/articles/Tips_for_Fun_Relaxation_and_Safety.jpg?v=1689108911"
                            className="card-img-top"
                            alt="Vacation"
                        />
                        <div className="card-body">
                            <h5 className="card-title">Vacation</h5>
                            <p className="card-text">
                                Imagine a perfect escape from the hustle and bustle...
                            </p>
                        </div>
                    </div>

                    <div className="card">
                        <img
                            src="https://res.cloudinary.com/https-www-isango-com/image/upload/f_auto/t_m_Prod/v1707117263/europe/italy/rome/7c98b953-6881-46f0-8ae3-62ba322f4663_1280.jpg"
                            className="card-img-top"
                            alt="Excursion"
                        />
                        <div className="card-body">
                            <h5 className="card-title">Excursion</h5>
                            <p className="card-text">
                                This card has supporting text below as a natural lead-in to additional content.
                            </p>
                        </div>
                    </div>

                    <div className="card">
                        <img
                            src="https://lviv.travel/image/news/c6/ec/c6ecb09f9959e0c800b66902472c8b02c6638015_1580736449.jpg?crop=5149%2C3446%2C82%2C0"
                            className="card-img-top"
                            alt="Shopping"
                        />
                        <div className="card-body">
                            <h5 className="card-title">Shopping</h5>
                            <p className="card-text">
                                This is a wider card with supporting text...
                            </p>
                        </div>
                    </div>
                </div>
            </div>


        </>
    );
};

export default HomePage;
