import { Outlet } from "react-router-dom";
import Navbar from "components/layout/Navbar";
import styled from "styled-components";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  overflow: overlay;
`;

const Home = () => {
  return (
    <Container>
      <Navbar />
      <Outlet />
    </Container>
  );
};

export default Home;