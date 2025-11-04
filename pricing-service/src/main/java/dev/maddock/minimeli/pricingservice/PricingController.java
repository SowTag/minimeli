package dev.maddock.minimeli.pricingservice;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
class PricingController extends PricingServiceGrpc.PricingServiceImplBase {

    private final PricingService pricingService;

    @Override
    public void getFinalPrice(GetFinalPriceRequest request, StreamObserver<GetFinalPriceResponse> responseObserver) {

        UUID id = UUID.fromString(request.getProductId());

        BigDecimal price = pricingService.getFinalPrice(id);


        responseObserver.onNext(GetFinalPriceResponse.newBuilder().setFinalPrice(price.toString()).build());
        responseObserver.onCompleted();

    }

    @Override
    public void updateBasePrice(SetBasePriceRequest request, StreamObserver<Empty> responseObserver) {

        UUID id = UUID.fromString(request.getProductId());

        BigDecimal basePrice = new BigDecimal(request.getBasePrice());

        pricingService.setBasePrice(id, basePrice);
        
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
